package mabubu0203.com.github.catcafe.api.controller.store;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import mabubu0203.com.github.catcafe.api.controller.store.helper.request.StoreCreateRequestMapper;
import mabubu0203.com.github.catcafe.api.controller.store.helper.request.StoreDeleteRequestMapper;
import mabubu0203.com.github.catcafe.api.controller.store.helper.request.StoreUpdateRequestMapper;
import mabubu0203.com.github.catcafe.api.controller.store.helper.response.StoreCreateResponseMapper;
import mabubu0203.com.github.catcafe.api.controller.store.helper.response.StoreDeleteResponseMapper;
import mabubu0203.com.github.catcafe.api.controller.store.helper.response.StoreUpdateResponseMapper;
import mabubu0203.com.github.catcafe.api.controller.store.service.StoreDeleteService;
import mabubu0203.com.github.catcafe.api.controller.store.service.StoreModifyService;
import mabubu0203.com.github.catcafe.api.controller.store.service.StoreRegisterService;
import org.openapitools.api.StoreCommandApi;
import org.openapitools.model.InlineResponse400;
import org.openapitools.model.InlineResponse401;
import org.openapitools.model.InlineResponse404;
import org.openapitools.model.InlineResponse409;
import org.openapitools.model.PatchObject;
import org.openapitools.model.PostObject;
import org.openapitools.model.StoreCreate;
import org.openapitools.model.StoreUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class StoreCommandApiController implements StoreCommandApi {

  private final StoreDeleteService deleteService;
  private final StoreModifyService modifyService;
  private final StoreRegisterService registerService;


  @Operation(
      tags = {"store_command",},
      summary = "????????????API",
      description = "?????????1???????????????",
      operationId = "storeCreate",
      security = {@SecurityRequirement(name = "ApiKeyAuth"),},
      responses = {
          @ApiResponse(responseCode = "200", description = "?????????", content = @Content(schema = @Schema(implementation = PostObject.class))),
          @ApiResponse(responseCode = "400", description = "??????????????????????????????", content = @Content(schema = @Schema(implementation = InlineResponse400.class))),
          @ApiResponse(responseCode = "401", description = "???????????????", content = @Content(schema = @Schema(implementation = InlineResponse401.class))),
      }
  )
  @Override
  public Mono<ResponseEntity<PostObject>> storeCreate(
      @Parameter(description = "??????????????????", schema = @Schema(allowableValues = {"cats"})) String cats,
      @Valid Mono<StoreCreate> storeCreate,
      ServerWebExchange exchange) {
    return storeCreate
        .map(new StoreCreateRequestMapper(cats))
        .flatMap(this.registerService::action)
        .map(new StoreCreateResponseMapper())
        .map(ResponseEntity.status(HttpStatus.OK)::body);
  }

  @Operation(
      tags = {"store_command",},
      summary = "????????????API",
      description = "?????????1?????????????????????",
      operationId = "storeDelete",
      security = {@SecurityRequirement(name = "ApiKeyAuth"),},
      responses = {
          @ApiResponse(responseCode = "204", description = "?????????"),
          @ApiResponse(responseCode = "401", description = "???????????????", content = @Content(schema = @Schema(implementation = InlineResponse401.class))),
          @ApiResponse(responseCode = "404", description = "?????????????????????", content = @Content(schema = @Schema(implementation = InlineResponse404.class))),
          @ApiResponse(responseCode = "409", description = "?????????????????????", content = @Content(schema = @Schema(implementation = InlineResponse409.class))),
      }
  )
  @Override
  public Mono<ResponseEntity<Void>> storeDelete(
      @Parameter(description = "??????????????????", schema = @Schema(allowableValues = {"cats"})) String cats,
      @Parameter(description = "??????ID", schema = @Schema(type = "integer")) Integer storeId,
      @Parameter(description = "??????????????????????????????", schema = @Schema(type = "integer")) @NotNull @Valid Integer version,
      ServerWebExchange exchange) {
    return
        new StoreDeleteRequestMapper(
            cats, storeId,
            version).get()
            .flatMap(this.deleteService::action)
            .map(new StoreDeleteResponseMapper())
            .filter(Boolean::booleanValue)
            .map(bool -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
  }

  @Operation(
      tags = {"store_command",},
      summary = "????????????API",
      description = "?????????1???????????????",
      operationId = "storeUpdate",
      security = {@SecurityRequirement(name = "ApiKeyAuth"),},
      responses = {
          @ApiResponse(responseCode = "200", description = "?????????", content = @Content(schema = @Schema(implementation = PatchObject.class))),
          @ApiResponse(responseCode = "400", description = "??????????????????????????????", content = @Content(schema = @Schema(implementation = InlineResponse400.class))),
          @ApiResponse(responseCode = "401", description = "???????????????", content = @Content(schema = @Schema(implementation = InlineResponse401.class))),
          @ApiResponse(responseCode = "404", description = "?????????????????????", content = @Content(schema = @Schema(implementation = InlineResponse404.class))),
          @ApiResponse(responseCode = "409", description = "?????????????????????", content = @Content(schema = @Schema(implementation = InlineResponse409.class))),
      }
  )
  @Override
  public Mono<ResponseEntity<PatchObject>> storeUpdate(
      @Parameter(description = "??????????????????", schema = @Schema(allowableValues = {"cats"})) String cats,
      @Parameter(description = "??????ID", schema = @Schema(type = "integer")) Integer storeId,
      @Valid Mono<StoreUpdate> storeUpdate,
      ServerWebExchange exchange) {
    return storeUpdate
        .map(new StoreUpdateRequestMapper(cats, storeId))
        .flatMap(this.modifyService::action)
        .map(new StoreUpdateResponseMapper())
        .map(ResponseEntity.status(HttpStatus.OK)::body);
  }

}
