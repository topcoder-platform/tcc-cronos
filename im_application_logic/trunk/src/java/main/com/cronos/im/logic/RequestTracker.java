package com.cronos.im.logic;

import java.util.HashSet;

public class RequestTracker {
    
    private class UserKey {
        private final long userId;
        
        private final long categoryId;
        
        public UserKey(long userId, long categoryId) {
            super();
            this.userId = userId;
            this.categoryId = categoryId;
        }
        
        public boolean equals(Object obj) {
            UserKey a = (UserKey) obj;
            return a.categoryId == this.categoryId && a.userId == this.userId;
        }
        
        public int hashCode() {
            return (int) (categoryId + userId);
        }        
    }
    
    private static RequestTracker instance = new RequestTracker();
    
    private static HashSet requesters = new HashSet();

    private RequestTracker() {}

    public static RequestTracker getInstance() {
        return instance;
    }
    
    public void addRequester(long userId, long categoryId) {
        requesters.add(new UserKey(userId, categoryId));
    }
    
    public boolean isRequesterPresent(long userId, long categoryId) {
        return requesters.contains(new UserKey(userId, categoryId));
    }
    
    public boolean removeRequester(long userId, long categoryId) {
        return requesters.remove(new UserKey(userId, categoryId));
    }
}
