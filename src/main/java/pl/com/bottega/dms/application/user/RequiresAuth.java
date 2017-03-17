package pl.com.bottega.dms.application.user;

public @interface RequiresAuth {

    String[] role() default {"STAFF"};

}
