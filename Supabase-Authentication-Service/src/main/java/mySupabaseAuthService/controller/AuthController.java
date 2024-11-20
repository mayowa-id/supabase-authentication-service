package mySupabaseAuthService.controller;

import mySupabaseAuthService.service.security.AuthService;
import mySupabaseAuthService.model.User;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignupRequest request) {
        User user = authService.signup(
                request.getEmail(),
                request.getPassword(),
                request.getFullName()
        );
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request) {
        User user = authService.login(
                request.getEmail(),
                request.getPassword()
        );
        return ResponseEntity.ok(user);
    }
}