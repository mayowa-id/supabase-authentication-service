package mySupabaseAuthService.service.security;

import mySupabaseAuthService.model.User;

@Service
@Slf4j
public class UserProfileService {
    private final SupabaseClient supabaseClient;

    @Autowired
    public UserProfileService(SupabaseClient supabaseClient) {
        this.supabaseClient = supabaseClient;
    }

    public User updateProfile(String userId, User updatedUser) {
        try {
            return supabaseClient.from("users")
                    .update(updatedUser)
                    .eq("id", userId)
                    .executeAndGetSingle(User.class);
        } catch (Exception e) {
            log.error("Error updating profile: ", e);
            throw new RuntimeException("Failed to update profile");
        }
    }

    public User getProfile(String userId) {
        try {
            return supabaseClient.from("users")
                    .select()
                    .eq("id", userId)
                    .single()
                    .executeAndGetSingle(User.class);
        } catch (Exception e) {
            log.error("Error fetching profile: ", e);
            throw new RuntimeException("Failed to fetch profile");
        }
    }
}