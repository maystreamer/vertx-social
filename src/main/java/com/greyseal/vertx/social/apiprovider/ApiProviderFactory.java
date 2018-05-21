package com.greyseal.vertx.social.apiprovider;

import io.vertx.reactivex.core.Vertx;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;

@SuppressWarnings("unchecked")
public class ApiProviderFactory {
    private static Map<String, Class<IApiProvider>> apiResourceMapping = new HashMap<>();
    private static Set<ApiProvider> apiProviders = new HashSet<>();

    static {
        final Reflections reflections = new Reflections("com.greyseal.vertx.social.apiprovider");
        final Set<Class<?>> clazzes = reflections.getTypesAnnotatedWith(ApiProvider.class);
        if (null != clazzes && !clazzes.isEmpty()) {
            clazzes.forEach(x -> {
                ApiProvider apiProvider = x.getAnnotation(ApiProvider.class);
                if (null != apiProvider && IApiProvider.class.isAssignableFrom(x)) {
                    apiProviders.add(apiProvider);
                    apiResourceMapping.put(apiProvider.name().type(), (Class<IApiProvider>) x);
                }
            });
        }
    }

    public static IApiProvider loadProvider(final String name, final Vertx vertx) throws ClassNotFoundException {
        Class<IApiProvider> clazz = null;
        IApiProvider apiProvider = null;
        try {
            clazz = apiResourceMapping.get(name);
            apiProvider = clazz.getDeclaredConstructor(Vertx.class).newInstance(vertx);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (null == apiProvider) {
            throw new ClassNotFoundException(String.join(" ", "Class ", name, " not found"));
        }
        return apiProvider;
    }
}
