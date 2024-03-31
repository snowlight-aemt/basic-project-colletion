package me.snowlight.springdistribute.config.database;


import lombok.Getter;

public class UserHolder {
    private static final ThreadLocal<Context> userContext = new ThreadLocal<>();

    static {
        userContext.set(new Context());
    }

    private static Context getContext() {
        return userContext.get();
    }

    public static void setSharding(ShardingTarget target, long shardKey) {
        getContext().setSharding(new Sharding(target, shardKey));
    }

    public static void clearSharding() {
        getContext().setSharding(null);
    }

    public static Sharding getSharding() {
        return getContext().getSharding();
    }

    public static class Context {
        private Sharding sharding;

        public void setSharding(Sharding sharding) {
            this.sharding = sharding;
        }

        public Sharding getSharding() {
            return this.sharding;
        }
    }

    @Getter
    public static class Sharding {
        private ShardingTarget target;
        private long shardKey;

        Sharding(ShardingTarget target, long shardKey) {
            this.target = target;
            this.shardKey = shardKey;
        }
    }
}


