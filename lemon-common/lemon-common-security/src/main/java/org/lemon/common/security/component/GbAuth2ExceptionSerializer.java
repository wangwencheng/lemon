package org.lemon.common.security.component;

import org.lemon.common.core.constant.enums.ErrorCodeEnum;
import org.lemon.common.security.exception.GbAuth2Exception;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.SneakyThrows;

/**
 * OAuth2 异常格式化
 * @author Donald
 */
public class GbAuth2ExceptionSerializer extends StdSerializer<GbAuth2Exception> {
	public GbAuth2ExceptionSerializer() {
		super(GbAuth2Exception.class);
	}

	@Override
	@SneakyThrows
	public void serialize(GbAuth2Exception value, JsonGenerator gen, SerializerProvider provider) {
		gen.writeStartObject();
		gen.writeObjectField("code", ErrorCodeEnum.FAIL.getCode());
		gen.writeStringField("msg", value.getMessage());
		gen.writeStringField("data", value.getErrorCode());
		gen.writeEndObject();
	}
}
