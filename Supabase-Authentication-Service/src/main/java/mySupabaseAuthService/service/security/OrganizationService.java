package mySupabaseAuthService.service.security;

import mySupabaseAuthService.model.Organization;

@Service
@Slf4j
public class OrganizationService {
    private final SupabaseClient supabaseClient;

    @Autowired
    public OrganizationService(SupabaseClient supabaseClient) {
        this.supabaseClient = supabaseClient;
    }

    public Organization createOrganization(String name, String ownerId) {
        try {
            Organization org = new Organization();
            org.setName(name);
            org.setOwnerId(ownerId);
            org.setMemberIds(Collections.singletonList(ownerId));

            PostgrestResult<Organization> result = supabaseClient.from("organizations")
                    .insert(org)
                    .execute();

            return result.getData().get(0);
        } catch (Exception e) {
            log.error("Error creating organization: ", e);
            throw new RuntimeException("Failed to create organization");
        }
    }

    public void addMemberToOrganization(String organizationId, String userId, String role) {
        try {
            // Add to organizations_users junction table
            supabaseClient.from("organizations_users")
                    .insert(Map.of(
                            "organization_id", organizationId,
                            "user_id", userId,
                            "role", role
                    ))
                    .execute();

            // Update user's organization reference
            supabaseClient.from("users")
                    .update(Map.of("organization_id", organizationId))
                    .eq("id", userId)
                    .execute();
        } catch (Exception e) {
            log.error("Error adding member to organization: ", e);
            throw new RuntimeException("Failed to add member");
        }
    }
}

