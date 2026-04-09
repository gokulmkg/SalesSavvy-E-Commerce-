package com.kodnest.ToyGalaxy.ServiceContract;

import com.kodnest.ToyGalaxy.entity.User;

public interface AuthServiceContract {
    public User authenticate(String username, String password);
    public String generateToken(User user);
    public String generateNewToken(User user);
    public void saveToken(User user, String token);
    public void logout(User user);
    public boolean validateToken(String token);
    public String extractUsername(String token);
}
