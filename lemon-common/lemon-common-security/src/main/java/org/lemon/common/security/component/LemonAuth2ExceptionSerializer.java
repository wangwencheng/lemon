package org.lemon.common.security.component;

import org.lemon.common.core.constant.enums.ErrorCodeEnum;
import org.lemon.common.security.exception.LemonAuth2Exception;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.SneakyThrows;

/**
 * OAuth2 异常格式化
 * @author wwc
 */
public class LemonAuth2ExceptionSerializer extends StdSerializer<LemonAuth2Exception> {
	public LemonAuth2ExceptionSerializer() {
		super(LemonAuth2Exception.class);
	}

	@Override
	@SneakyThrows
	public void serialize(LemonAuth2Exception value, JsonGenerator gen, SerializerProvider provider) {
		gen.writeStartObject();
		gen.writeObjectField("code", ErrorCodeEnum.FAIL.getCode());
		gen.writeStringField("msg", value.getMessage());
		gen.writeStringField("data", value.getErrorCode());
		gen.writeEndObject();
	}
}
