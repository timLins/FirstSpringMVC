package ru.alishev.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alishev.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Neil Alishev
 */
@Component
public class PersonDAO {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    private static Connection connection;
//
//
//    static {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
//            if (connection != null) {
//                System.out.println("Connected");
//            }
//        } catch (SQLException e) {
//            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
//        }
//    }


    public List<Person> index() {
//
//        List<Person> people = new ArrayList<>();
//        String SQL = "select * from person;";
//        try {
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(SQL);
//            while (resultSet.next()) {
//                Person person = new Person();
//                person.setId(resultSet.getInt("id"));
//                person.setName(resultSet.getString("name"));
//                person.setAge(resultSet.getInt("age"));
//                person.setEmail(resultSet.getString("email"));
//                people.add(person);
//            }
//
//        } catch (SQLException e) {
//            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
//        }
//        return people;
        return jdbcTemplate.query("select * from person;", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
//        Person person = null;
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(
//                    "select * from person where id=?"
//            );
//            preparedStatement.setInt(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.next();
//            person = new Person();
//            person.setId(resultSet.getInt("id"));
//            person.setName(resultSet.getString("name"));
//            person.setEmail(resultSet.getString("email"));
//            person.setAge(resultSet.getInt("age"));
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return person;
        return jdbcTemplate
                .query("select * from person where id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
//        String SQL = "INSERT INTO Person VALUES(" + 1 + ",'" + person.getName() + "',"
//                + person.getAge() + ",'" + person.getEmail() + "')";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(
//                    "insert into person values (1,?,?,?)");
//            preparedStatement.setString(1, person.getName());
//            preparedStatement.setString(3, person.getEmail());
//            preparedStatement.setInt(2, person.getAge());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        jdbcTemplate
                .update("insert into person values (1,?,?,?)",
                        person.getName(), person.getAge(), person.getEmail());

    }

    public void update(int id, Person updatedPerson) {
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(
//                    "update person set name=?, age=?,email=? where id=?"
//            );
//            preparedStatement.setString(1, updatedPerson.getName());
//            preparedStatement.setString(3, updatedPerson.getEmail());
//            preparedStatement.setInt(2, updatedPerson.getAge());
//            preparedStatement.setInt(4, id);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//       Person personUPD = show(id);
//       personUPD.setName(person.getName());
//       personUPD.setAge(person.getAge());
//       personUPD.setEmail(person.getEmail());
        jdbcTemplate.update("update person set name=?, age=?,email=? where id=?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }

    public void delete(int idDelete) {
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("delete  from person where id=?");
//            preparedStatement.setInt(1, idDelete);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        jdbcTemplate.update("delete  from person where id=?", idDelete);
    }


    //bath ///////////////////////////

    ///test bath

    //////////////////////////////////


    public void testMultipleUpdate() {
        List<Person> people = create1000People();
        long time = System.currentTimeMillis();
        for (Person person : people) {
            jdbcTemplate
                    .update("insert into person values (?,?,?,?)", person.getId(),
                            person.getName(), person.getAge(), person.getEmail());
        }
        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - time));
    }

    public void testBatchUpdate() {
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("insert into person values (?,?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1,people.get(i).getId());
                ps.setString(2,people.get(i).getName());
                ps.setInt(3,people.get(i).getAge());
                ps.setString(4,people.get(i).getEmail());
            }

            @Override
            public int getBatchSize() {
                return people.size();
            }

        });

        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }

    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i, "Name" + i, 30, "test" + i + "@mail.ru"));
        }
        return people;
    }


}
