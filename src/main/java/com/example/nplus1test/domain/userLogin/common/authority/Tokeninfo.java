package com.example.nplus1test.domain.userLogin.common.authority;

public class Tokeninfo {
    private final String grantType;
    private final String accessToken;

    public Tokeninfo(String grantType, String accessToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String toString() {
        return "TokenInfo{" +
                "grantType='" + grantType + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tokeninfo)) return false;
        Tokeninfo that = (Tokeninfo) o;
        return grantType.equals(that.grantType) &&
                accessToken.equals(that.accessToken);
    }

    @Override
    public int hashCode() {
        int result = grantType.hashCode();
        result = 31 * result + accessToken.hashCode();
        return result;
    }
}

