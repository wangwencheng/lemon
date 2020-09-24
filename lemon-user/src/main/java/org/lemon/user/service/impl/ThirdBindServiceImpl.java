package org.lemon.user.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lemon.common.core.util.R;
import org.lemon.user.api.dto.request.BindDTO;
import org.lemon.user.service.BindService;
import org.springframework.stereotype.Service;

@Slf4j
@Service("thirdBind")
@AllArgsConstructor
public class ThirdBindServiceImpl implements BindService {
	@Override
	public R binding(BindDTO request) {
		return null;
	}
}
