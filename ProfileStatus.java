package group.lsg.resultinvestmentapp.Class;

public enum ProfileStatus {
        PROFILE_CREATED(0), NOT_AUTHORIZED(1), NO_PROFILE(2), IS_ADMIN(3),
        IS_INVESTOR(4), IS__SUPER_ADMIN(5), IS_AUTHORIZED(6);

        int status;

        ProfileStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;

        }
    }

