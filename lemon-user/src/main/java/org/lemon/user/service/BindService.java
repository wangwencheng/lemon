package org.lemon.user.service;

import org.lemon.common.core.util.R;
import org.lemon.user.api.dto.request.BindDTO;

public interface BindService {
	R binding(BindDTO request);
}
