package sample.db;

/**
 * здесь размещены все запросы
 * которые выполняет программа
 * дергаются из
 * @see SqlControl
 */
public class Query {
    //region #Procedures
    public static final String createDoctorProcedure = "create procedure if not exists getDoctorProcedure() " +
            "begin " +
            "select * from doctor; " +
            "end;";
    public static final String createOwnerProcedure = "create procedure if not exists getOwnerProcedure() " +
            "begin " +
            "select * from owner; " +
            "end;";
    public static final String createAnimalProcedure = "create procedure if not exists getAnimalProcedure() " +
            "begin " +
            "select * from animal where animal_status = 1; " +
            "end;";
    public static final String createCabinetProcedure = "create procedure if not exists getCabinetProcedure() " +
            "begin " +
            "select * from cabinet; " +
            "end;";
    public static final String createKindProcedure = "create procedure if not exists getKindProcedure() " +
            "begin " +
            "select * from kind; " +
            "end;";
    public static final String createLastUpdateProcedure = "create procedure if not exists getLastUpdate(in a int) " +
            "begin " +
            "select changedat from doctor_audit where id in (select max(id) from doctor where id = a); " +
            "end;";
    public static final String callDoctorProcedure = "call getDoctorProcedure; ";
    public static final String callOwnerProcedure = "call getOwnerProcedure; ";
    public static final String callAnimalProcedure = "call getAnimalProcedure; ";
    public static final String callCabinetProcedure = "call getCabinetProcedure; ";
    public static final String callKindProcedure = "call getKindProcedure; ";
    //endregion

    //region #Trigger
    public static final String tableForTrigger = "create table if not exists doctor_audit( id int auto_increment primary key," +
            "doctor_id int, action varchar(50), changedat VARCHAR(50) not null);";

    public static final String createTrigger = "create trigger if not exists doctor_audit_update before update on doctor " +
                        "for each row " +
                        "begin " +
                        "insert into doctor_audit values (null, old.doctor_id, 'update', now());" +
                        "end;";
    //endregion

}
