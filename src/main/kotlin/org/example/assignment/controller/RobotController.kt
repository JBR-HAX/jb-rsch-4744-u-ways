package org.example.assignment.controller

import org.example.assignment.model.Coordinate
import org.example.assignment.model.Movement
import org.example.assignment.model.Robot
import org.example.assignment.repository.RobotRepository
import org.example.assignment.service.LocationsService
import org.example.assignment.service.MovesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class RobotController(
    @Autowired val robotRepository: RobotRepository,
    @Autowired val locationsService: LocationsService,
    @Autowired val movesService: MovesService,
) {
    @PostMapping("/locations")
    fun deriveCoordinates(@RequestBody(required = true) movements: Array<Movement>) = with(movements.toList()) {
        robotRepository
            .save(Robot(movements = this, coordinates = locationsService.derive(this)))
            .coordinates
    }

    @PostMapping("/moves")
    fun deriveMovements(@RequestBody(required = true) coordinates: Array<Coordinate>) = with(coordinates.toList()) {
        robotRepository
            .save(Robot(movements = movesService.derive(this), coordinates = this))
            .movements
    }
}