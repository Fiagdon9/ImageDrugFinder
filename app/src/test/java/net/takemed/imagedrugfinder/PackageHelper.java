package net.takemed.imagedrugfinder;

public class PackageHelper {

    public static final String DEFAULT_PACKAGE = "net.takemed.imagedrugfinder";


    public static Class hasClass(String className)
            throws ClassNotFoundException {
        return hasClass(DEFAULT_PACKAGE, className);
    }

    public static Class hasClass(String defaultPackage, String className)
            throws ClassNotFoundException {
        String pckg = String.format("%s.%s", defaultPackage, className);

        return Class.forName(pckg);
    }

}
