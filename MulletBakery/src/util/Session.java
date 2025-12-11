package util;

import model.Akun;

public class Session {

    private static Akun currentAkun;

    public static void setCurrentAkun(Akun akun) {
        currentAkun = akun;
    }

    public static Akun getCurrentAkun() {
        return currentAkun;
    }

    public static boolean isLoggedIn() {
        return currentAkun != null;
    }

    public static boolean isAdmin() {
        return currentAkun != null && "admin".equalsIgnoreCase(currentAkun.getRole());
    }

    public static void clear() {
        currentAkun = null;
    }
}
