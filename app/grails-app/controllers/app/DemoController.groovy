package app

class DemoController {

    PersonService personService

    def index() {
        int numberOfPeople = personService.count()

        render "There are currently $numberOfPeople people."
    }
}
