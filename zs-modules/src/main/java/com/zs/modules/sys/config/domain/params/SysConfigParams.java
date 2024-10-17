package com.zs.modules.sys.config.domain.params;

import com.zs.common.core.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysConfigParams extends BaseEntity {

    private String configKey;

    private String configValue;
}
