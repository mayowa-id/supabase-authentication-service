package mySupabaseAuthService.security;

import mySupabaseAuthService.model.User;
import mySupabaseAuthService.security.RequiresRole;

import java.nio.file.AccessDeniedException;

@Aspect
@Component
public class RoleBasedAuthorizationAspect {
    @Around("@annotation(requiresRole)")
    public Object checkRole(ProceedingJoinPoint joinPoint, RequiresRole requiresRole) throws Throwable {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        if (user.getRoles().contains(requiresRole.value())) {
            return joinPoint.proceed();
        }

        throw new AccessDeniedException("Insufficient privileges");
    }
}
