workspace "Social Media Microservices" "Microservice video social media" {

    model {
        u = person "User"
        s = softwareSystem "Social Media System" {
            cli = container "CLI Client"
            init-kafka = container "Kafka Topic Initialiser"
            micronautVM = container "Video Microservice" {
                domain = component "Domain objects and DTOs"
                repos = component "Repositories"
                events = component "Kafka consumers and producers"
                resources = component "Resources"
            }
            micronautTHM = container "Trending Hashtags Microservice" {
                domainTHM = component "Domain objects and DTOs"
                reposTHM = component "Repositories"
                eventsTHM = component "Kafka consumers and producers"
                resourcesTHM = component "Resources"
            }
            micronautSM = container "Subscription Microservice" {
                domainSM = component "Domain objects and DTOs"
                reposSM = component "Repositories"
                eventsSM = component "Kafka consumers and producers"
                resourcesSM = component "Resources"
            }
            databaseTHM = container "Hashtags Database" "" "MariaDB" "database"
            databaseSM = container "Subscription Database" "" "MariaDB" "database"

            database = container "Video Database" "" "MariaDB" "database"
            kafka = container "Kafka Cluster"
        }


        u -> cli "Uses"

        cli -> micronautVM "Interacts with HTTP API"
        cli -> micronautTHM "Interacts with HTTP API"
        cli -> micronautSM "Interacts with HTTP API"
        init-kafka -> kafka "Creates topics in"
        micronautVM -> database "Reads from and writes to"
        micronautTHM -> databaseTHM "Reads from and writes to"
        micronautSM -> databaseSM "Reads from and writes to"

        micronautVM -> kafka "Consumes and produces events"
        micronautTHM -> kafka "Consumes and produces events"
        micronautSM -> kafka "Consumes and produces events"

        // VM Component diagram
        repos -> domain "Creates and updates"
        repos -> database "Queries and writes to"
        resources -> repos "Uses"
        resources -> events "Uses"
        resources -> domain "Reads and updates"
        cli -> resources "Invokes"
        events -> kafka "Consumes and produces events in"


        // THM Component diagram
        reposTHM -> domainTHM "Creates and updates"
        reposTHM -> databaseTHM "Queries and writes to"
        resourcesTHM -> reposTHM "Uses"
        resourcesTHM -> eventsTHM "Uses"
        resourcesTHM -> domainTHM "Reads and updates"
        cli -> resourcesTHM "Invokes"
        eventsTHM -> kafka "Consumes and produces events in"

        // SM Component diagram
        reposSM -> domainSM "Creates and updates"
        reposSM -> databaseSM "Queries and writes to"

        resourcesSM -> reposSM "Uses"
        resourcesSM -> eventsSM "Uses"
        resourcesSM -> domainSM "Reads and updates"
        cli -> resourcesSM "Invokes"
        eventsSM -> kafka "Consumes and produces events in"
    }

    views {
        theme default
        systemContext s {
            include *
        }
        container s {
            include *
        }
        component micronautVM {
            include *
        }
        component MicronautTHM {
            include *
        }
        component MicronautSM {
            include *
        }
        styles {
            element "database" {
                shape Cylinder
            }
            element "webapp" {
                shape WebBrowser
            }
            element external {
                background gray
            }
        }
    }

}