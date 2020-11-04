import enums.DisciplineType;
import enums.Status;
import model.Discipline;
import model.Role;
import model.Task;
import model.User;
import service.DisciplineService;
import service.RoleService;
import service.TaskService;
import service.UserService;
import utils.HibernateUtils;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class StoreData {
    private static final UserService userService = new UserService();
    private static final RoleService roleService = new RoleService();
    private static final TaskService taskService = new TaskService();
    private static final DisciplineService disciplineService = new DisciplineService();

    public static void main(String[] args) throws SQLException {
        Set<Role> roles = roleFactory();
        List<Task> tasksAM = taskFactoryAm();
//        List<Task> tasksDev = taskFactoryDev();
        List<Discipline> disciplineList = discipline();
        List<User> userList = AmUserFactory(roles.stream().filter(it -> it.getName().equals("Intern")).collect(Collectors.toSet()), tasksAM, disciplineList.get(0));
//        List<User> userList = DevUserFactory(roles.stream().filter(it -> it.getName().equals("Intern")).collect(Collectors.toSet()), tasks, disciplineList.get(1));

        // Populate tables with some data using hibernate.
        for (Role role : roles) {
            roleService.add(role);
        }
        for (Task task : tasksAM) {
            taskService.add(task);
        }
        for (Discipline discipline : disciplineList) {
            disciplineService.add(discipline);
        }
        for (User user : userList) {
            userService.add(user);
        }

        //Set HeadOfDiscipline for AM
        disciplineList.get(0).setHeadOfDiscipline(userList.get(0));
        disciplineService.update(disciplineList.get(0));

        //Set HeadOfDiscipline for DEV
//        disciplineList.get(1).setUser(userList.get(3)); //Dorel
//        disciplineService.update(disciplineList.get(1));

        //Adding 1 user for DEV discipline
        disciplineList.get(1).setUsers(Arrays.asList(DevUserFactory(
                roles.stream().filter(it -> it.getName().equals("Employee")).collect(Collectors.toSet())
                , tasksAM, disciplineList.get(1)).get(1)));
        disciplineService.update(disciplineList.get(1));

        //Adding users for Test discipline
        disciplineList.get(2).setUsers(TestUserFactory(
                roles.stream().filter(it -> it.getName().equals("Employee") || it.getName().equals("Intern")).collect(Collectors.toSet())
                , tasksAM, disciplineList.get(2)));
        disciplineService.update(disciplineList.get(2));

        //Adding users for DEV and Test Head
        User DevHead = new User("Andrei", "Drumov", "andreidrumov@endava.com", "drumova", Date.valueOf(LocalDate.now()), true, disciplineList.get(1), Collections.EMPTY_LIST
                , roles.stream().filter(it -> it.getName().equals("Head")).collect(Collectors.toSet()));
        userService.add(DevHead);

        User TestHead = new User("Vitalie", "Valachi", "vitalievalachi@endava.com", "valachiv", Date.valueOf(LocalDate.now()), true, disciplineList.get(2), Collections.EMPTY_LIST
                , roles.stream().filter(it -> it.getName().equals("Head")).collect(Collectors.toSet()));
        userService.add(TestHead);

        //Set DEV and Test HEAD
        disciplineList.get(1).setHeadOfDiscipline(DevHead);
        disciplineList.get(2).setHeadOfDiscipline(TestHead);
        disciplineService.update(disciplineList.get(1));
        disciplineService.update(disciplineList.get(2));
        printSeparator();

        //List all users with a specific role name.
        List<User> list = userService.getUserByRole("Employee");
        for (User user : list) {
            System.out.println(user);
            printSeparator();
        }
        userService.closeTransactionSession();
        printSeparator();

        //List all users from a discipline (let’s say Applications Management).
        List<User> AmUsers = userService.getUsersByDiscipline(DisciplineType.AM);
        for (User user : AmUsers) {
            System.out.println(user);
            printSeparator();
        }
        userService.closeTransactionSession();

        //List All Users with Task StatusTODO
        List<User> UsersWithTODO = userService.getUsersByTaskStatus(Status.TODO);
        for (User user : UsersWithTODO) {
            System.out.println(user);
            printSeparator();
        }
        userService.closeTransactionSession();

        //Change the HeadOfDiscipline for Development discipline.
        User newDevHeadUser = new User("Dumitru", "Laptecru", "dimalapteacru@endava.com", "dima", Date.valueOf(LocalDate.now()), true, disciplineList.get(1), Collections.EMPTY_LIST
                , roles.stream().filter(it -> it.getName().equals("Head")).collect(Collectors.toSet()));
        userService.add(newDevHeadUser);
        Discipline DevDiscipline = disciplineService.getDiscipline(DisciplineType.DEV);
        System.out.println(DevDiscipline);
        disciplineService.closeTransactionSession();
        printSeparator();

        DevDiscipline.setHeadOfDiscipline(newDevHeadUser);
        disciplineService.update(DevDiscipline);
        System.out.println(disciplineService.getDiscipline(DisciplineType.DEV));
        disciplineService.closeTransactionSession();
        printSeparator();

        //Delete a user
        List<User> userById = userService.getUserById(2);
        userService.closeTransactionSession();

        if(userById.size() == 1)
            userService.delete(userById.get(0));

        //Delete a role ????
//        List<Role> roleDelete = roleService.getRoleByName("Intern");
//        roleService.closeTransactionSession();
//
//        if(roleDelete.size() == 1)
//            roleService.delete(roleDelete.get(0));

        HibernateUtils.closeSessionFactory();
    }

    private static Set<Role> roleFactory() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("Head"));
        roles.add(new Role("Employee"));
        roles.add(new Role("Intern"));

        return roles;
    }

    public static List<Discipline> discipline() {
        List<Discipline> disciplines = new ArrayList<>();
        disciplines.add(new Discipline(DisciplineType.AM));
        disciplines.add(new Discipline(DisciplineType.DEV));
        disciplines.add(new Discipline(DisciplineType.TEST));

        return disciplines;
    }

    private static List<User> AmUserFactory(Set<Role> roleSet, List<Task> tasks, Discipline discipline) {
        List<User> users = new ArrayList<>();
        users.add(new User("nicolae", "semitar", "semitar@endava.com", "nsemitar", Date.valueOf(LocalDate.now()), true,
                discipline, Arrays.asList(tasks.get(0)), roleSet));
        users.add(new User("dima", "cretu", "dima@endava.com", "dima95", Date.valueOf(LocalDate.now()), true,
                discipline, Arrays.asList(tasks.get(1)), roleSet));
        users.add(new User("ion", "railean", "irailean@endava.com", "railean", Date.valueOf(LocalDate.now()), true,
                discipline, Arrays.asList(tasks.get(2)), roleSet));
        users.add(new User("dorel", "buhnici", "dorel@endava.com", "dorel", Date.valueOf(LocalDate.now()), true,
                discipline, Arrays.asList(tasks.get(3)), roleSet));


        return users;
    }

    private static List<User> TestUserFactory(Set<Role> roleSet, List<Task> tasks, Discipline discipline) {
        List<User> users = new ArrayList<>();
        users.add(new User("alina", "hadjivu", "alin@endava.com", "alina", Date.valueOf(LocalDate.now()), true,
                discipline, Arrays.asList(tasks.get(0)), roleSet));
        users.add(new User("veronica", "ioana", "vero@endava.com", "vero", Date.valueOf(LocalDate.now()), true,
                discipline, Arrays.asList(tasks.get(1)), roleSet));
        users.add(new User("vadim", "besliu", "vadim@endava.com", "vadim", Date.valueOf(LocalDate.now()), true,
                discipline, Arrays.asList(tasks.get(2)), roleSet));
        users.add(new User("adrian", "pricop", "pricop@endava.com", "adrian", Date.valueOf(LocalDate.now()), true,
                discipline, Arrays.asList(tasks.get(3)), roleSet));

        return users;
    }

    private static List<User> DevUserFactory(Set<Role> roleSet, List<Task> tasks, Discipline discipline) {
        List<User> users = new ArrayList<>();
        users.add(new User("cristi", "dodita", "dod@endava.com", "dodita",  Date.valueOf(LocalDate.now()), true,
                discipline, Arrays.asList(tasks.get(0)), roleSet));
        users.add(new User("artur", "vieru", "vieru@endava.com", "artur", Date.valueOf(LocalDate.now()), true,
                discipline, Arrays.asList(tasks.get(1)), roleSet));
        users.add(new User("stefan", "josu", "josus@endava.com", "stef", Date.valueOf(LocalDate.now()), true,
                discipline, Arrays.asList(tasks.get(2)), roleSet));
        users.add(new User("costea", "utrei", "costea@endava.com", "costea", Date.valueOf(LocalDate.now()), true,
                discipline, Arrays.asList(tasks.get(3)), roleSet));

        return users;
    }

    public static List<Task> taskFactoryAm() {
        return Arrays.asList(new Task("Hibernate", "Hibernate 10 subTasks to be Done", Date.valueOf(LocalDate.of(2020, 10, 31)), Date.valueOf(LocalDate.of(2020,11,30)), Status.DONE),
                new Task("UI - HTML", "A post card in HTML", Date.valueOf(LocalDate.of(2020, 10, 31)), Date.valueOf(LocalDate.of(2020,11,15)), Status.PROGRESS),
                new Task("UI - CSS", "A family Tree in CSS", Date.valueOf(LocalDate.of(2020, 10, 31)), Date.valueOf(LocalDate.of(2020,11,18)), Status.TODO),
                new Task("JavaCore", "JavaCore Exceptions", Date.valueOf(LocalDate.of(2020, 10, 31)), Date.valueOf(LocalDate.of(2020,11,02)), Status.PROGRESS));

    }

    public static List<Task> taskFactoryDev() {
        return Arrays.asList(new Task("Clouds", "Clouds",Date.valueOf(LocalDate.of(2020, 10, 31)), Date.valueOf(LocalDate.of(2020,11,02)), Status.TODO),
                new Task("Basic Code 1", "Basic Code 1", Date.valueOf(LocalDate.of(2020, 10, 31)), Date.valueOf(LocalDate.of(2020,11,13)), Status.DONE),
                new Task("Basic Code 2", "Basic Code 2", Date.valueOf(LocalDate.of(2020, 10, 31)), Date.valueOf(LocalDate.of(2020,11,14)), Status.DONE),
                new Task("Basic Code 3", "Basic Code 3", Date.valueOf(LocalDate.of(2020, 10, 31)), Date.valueOf(LocalDate.of(2020,11,15)), Status.PROGRESS));

    }

    public static void printSeparator() {
        System.out.println("<=======================================================================================>");
    }
}


