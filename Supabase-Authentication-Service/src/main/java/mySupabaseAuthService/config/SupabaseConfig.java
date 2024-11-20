package mySupabaseAuthService.config;

@Configuration
public class SupabaseConfig {
    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    @Bean
    public SupabaseClient supabaseClient() {
        return new SupabaseClient(supabaseUrl, supabaseKey);
    }
}