package sample;

public interface UserQueries {
     String UPDATE_USER_QUERY = "insert into users (name,password,vector) values(?,?,?)";
     String SELECT_ALL_USERS = "select u.* from users u";
     String SELECT_USER_BY_ID = "select u.* from users u where u.id = ?";
}
