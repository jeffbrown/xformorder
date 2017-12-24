package app

import grails.gorm.services.Service
import xformorder.SayHello

@Service(Person)
interface PersonService {

    @SayHello
    int count()
}
