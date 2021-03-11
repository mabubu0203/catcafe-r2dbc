package mabubu0203.com.github.catcafe.infra.source.redis.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import mabubu0203.com.github.catcafe.common.source.redis.entity.BaseHash;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = false)
@RedisHash(value = "XApiKey")
public class XApiKey extends BaseHash {

  @Id
  private Integer id;
  @Indexed
  private String token;

  private String clientIp;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;

}
