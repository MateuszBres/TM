package com.example.task_manager_backend.auth;


import com.example.task_manager_backend.user.UserFacade;
import com.example.task_manager_backend.user.dto.CurrentUserDto;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserFacade userFacade;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class) &&
                (parameter.getParameterType().equals(CurrentUserDto.class) ||
                        parameter.getParameterType().equals(CurrentUserDto.class));
    }

    @Override
    public @Nullable CurrentUserDto resolveArgument(MethodParameter parameter,
                                                    @Nullable ModelAndViewContainer mavContainer,
                                                    NativeWebRequest webRequest,
                                                    @Nullable WebDataBinderFactory binderFactory)
            throws Exception {
        CurrentUserDto user = userFacade.getCurrentUserDto();
        if (parameter.getParameterType().equals(CurrentUserDto.class)) {
            return new CurrentUserDto(user.id());
        }
        return user;
    }
}
