package pl.com.bottega.dms.infrastructure.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import pl.com.bottega.dms.application.user.AuthRequiedException;
import pl.com.bottega.dms.application.user.CurrentUser;
import pl.com.bottega.dms.application.user.RequiresAuth;

import java.util.Set;

@Component
@Aspect
public class AuthAspect {

    private CurrentUser currentUser;

    public AuthAspect(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    @Before(value = "@within(requiresAuth)")
    public void ensureClassAuth(RequiresAuth requiresAuth) {
        isUserAuth();
        isUserHasRole(requiresAuth);
    }
    @Before(value = "@annotation(requiresAuth)")
    public void ensureMethodAuth(RequiresAuth requiresAuth){
        isUserAuth();
        isUserHasRole(requiresAuth);
    }

    private void isUserHasRole(RequiresAuth requiresAuth) {
        String[] roles = requiresAuth.role();
        Set<String> currentUserRoles = currentUser.getRoles();
        for (String role : roles) {
            if (!currentUserRoles.contains(role)) {
                throw new AuthRequiedException("You have no priveleges for this operation");
            }
        }
    }

    private void isUserAuth() {
        if (currentUser.getEmployeeId() == null) {
            throw new AuthRequiedException("You heave to be authorized");
        }
    }


}
