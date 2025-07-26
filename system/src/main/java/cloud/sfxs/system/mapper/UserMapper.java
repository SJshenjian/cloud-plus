package cloud.sfxs.system.mapper;

import cloud.sfxs.system.config.mybatis.MyBaseMapper;
import cloud.sfxs.system.model.User;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shenjian
 * @since 2023-08-25
 */
@Repository
public interface UserMapper extends MyBaseMapper<User> {

}
