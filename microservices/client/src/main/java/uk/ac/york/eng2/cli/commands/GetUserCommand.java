package uk.ac.york.eng2.cli.commands;

import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.cli.clients.UsersClient;
import uk.ac.york.eng2.cli.domain.User;

import java.util.HashSet;
import java.util.Set;

@CommandLine.Command(
        name = "get-user",
        description = "gets a user by id, or by name",
        mixinStandardHelpOptions = true)
public class GetUserCommand implements Runnable {

    @Inject
    private UsersClient userClient;
    @CommandLine.Option(names = "-name")
    private String name;
    @CommandLine.Option(names = "-id")
    private Long id;

    @Override
    public void run() {
        if (id != null){
            User user = userClient.getUser(id);
            if (user == null){
                System.out.println("User with id " + id + " does not exist");
                return;
            }
            System.out.println(user.getId() + " - " + user.getName());
            return;
        }
        if (name != null){
            Iterable<User> usersAll = userClient.list();
            Set<User> users = new HashSet<>();

            for (User user : usersAll) {
                if (user.getName().equals(name)) {
                    users.add(user);
                }
            }
            if (users.isEmpty()){
                System.out.println("User with name " + name + " does not exist");
                return;
            }
            users.forEach(v -> System.out.println(v.getId() + " - " + v.getName()));

        }


    }
}
