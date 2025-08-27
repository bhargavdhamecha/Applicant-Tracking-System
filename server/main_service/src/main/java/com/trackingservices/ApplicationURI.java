package com.trackingservices;

public class ApplicationURI {
    private ApplicationURI(){}

    public static final String USER = "user";
    public static final String HR ="hr";
    public static final String ADD_REJECTION_REASON = "addRejectionReason";
    public static final String GET_ALL_REJECTION_REASONS = "getAllRejectionreasons";

    public static final String GET_REJECTION_REASON = "getRegectionreason";

    public static final String UPDATE_REJECTION_REASON = "updateRegectionReason";
    public static final String ADD_STAGE = "addStege";
    public static final String GET_ALL_STAGES="getAllStages" ;

    public static final String GET_STAGE="getStage";

    public static final String UPDATE_STAGE_NAME="updateStageName";

    public static final String GET_ALL_ROLE = "/getAllRole";
    public static final String UPDATE_ROLE="/updateRole";
    public static final String ADD_ROLE="/addRole";
    public static final String ADD_PERMISSION="/addPermission";
    public static final String UPDATE_PERMISSION="/updatePermission/{id}";
    public static final String GET_PERMISSIONS = "/getAllPermission";
    public static final String GET_ALL_APPLICANT="/getAllUser/{offset}/{length}/{field}";
    public static final String GET_USER_TRACKING_ID="/user/trackingId/{id}";
    public static final String UPDATE_USER_TRACKING="/updateUserTracking";
    public static final String CREATE_USER_TRACKING="/createUserTracking";
    public static final String GET_NEW_USER="/getNewUser";
    public static final String GET_PERMISSION_FOR_ROLE="/getPermission/{rolename}";
    public static final String MAIN="/main/api/admin";
    public static final String GET_STAGE_STATUS_STREAM="get/{stage}/{status}/{stream}";
    public static final String GET_STAGE_STATUS_STREAM_ROUND="get/{stage}/{status}/{stream}/{round}";
    public static final String GET_STAGE_STREAM="{stage}/{stream}";
    public static final String STAGE_STREAM_ROUND="{stage}/{stream}/{round}";
    public static final String GET_STATUS_STREAM="get/{status}/{stream}";
    public static final String STATUS_STREAM_ROUND="gets/{status}/{stream}/{round}";
    public static final String GET_STREAM="get/{stream}";

}
