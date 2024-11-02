package MyServices;

import dataaccess.DataAccess;

//Service for Clear Application
public class DeleteService {
    private DataAccess dataAccessor;
    public DeleteService(DataAccess dataAccessor) {
        this.dataAccessor = dataAccessor;
    }

    public void delete(){
        dataAccessor.clearAuthData();
        dataAccessor.clearUserData();
        dataAccessor.clearGameData();
    }


}
