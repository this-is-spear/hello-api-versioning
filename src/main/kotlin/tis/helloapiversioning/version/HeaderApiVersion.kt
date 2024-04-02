package tis.helloapiversioning.version

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class HeaderApiVersion(val value: Int)
