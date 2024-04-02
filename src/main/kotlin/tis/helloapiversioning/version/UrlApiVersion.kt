package tis.helloapiversioning.version

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class UrlApiVersion(val value: Int)
