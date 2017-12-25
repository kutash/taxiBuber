package com.kutash.taxibuber.main;

public class Main {


    /*public static void main(String[] args) throws SQLException, DAOException, IOException {
        *//*UserService userService = new UserService();
        userService.findAll().forEach(System.out::println);
        dao.delete(13);
        User user = dao.findEntityById(15);
        System.out.println(user);
        user.setName("pizda");
        user.setSurname("pizdovna");
        User user1 = dao.update(user);
        System.out.println(user1);
        dao.findAll().forEach(System.out::println);

        AddressDAO addressDAO = new DAOFactory().getAddressDAO();
        addressDAO.delete(3);

        UserDTO user1 = new UserDTO(0,3.2f,"Василий","Васин","Васильевич","dfsdfsd","mutmut@mail.ru","7777", UserRole.CLIENT, Date.valueOf("1969-07-15"),null);
        UserService service = new UserService();
        int id = service.create(user1);
        System.out.println(id);
        service.isUniqueEmail(user1.getEmail());
        ConnectionPool.getInstance().destroyConnections();

        PasswordEncryptor passwordEncryptor2 = new PasswordEncryptor("galina23".getBytes());
        String s2 = passwordEncryptor2.encrypt("ffffffffffffffffffffffffffffffutijroldpelvbhljfyyp");
        System.out.println(s2);*//*




        String key = "galina123@mail.ru".substring(0,8);
        PasswordEncryptor encryptor = new PasswordEncryptor(key.getBytes());
        String psw =  encryptor.decrypt("vLDZPdTMRZk=");
        System.out.println(psw);

    }*/
    byte b = (byte) 255;
}