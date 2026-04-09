package com.kodnest.ToyGalaxy.ServiceContract;


import com.kodnest.ToyGalaxy.entity.User;

import java.util.Map;

public interface OrderServiceContract {
    public Map<String, Object> getOrdersForUser(User user);
}
