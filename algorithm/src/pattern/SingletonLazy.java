package pattern;

public class SingletonLazy {
    // 私有
    private static volatile SingletonLazy instance;

    // 私有构造方法
    private SingletonLazy() {}

    // 提供给
    public static SingletonLazy getInstance(){
        if (instance == null) {
            synchronized (SingletonLazy.class) {
                if (instance == null) {
                    instance = new SingletonLazy();
                }
            }
        }
        return instance;

    }
}
