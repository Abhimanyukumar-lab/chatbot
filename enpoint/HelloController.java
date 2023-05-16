/**
 * 
 */
package com.technojade.allybot.enpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BC70873
 *
 */
@RestController
@RequestMapping(value = "/check")
public class HelloController {

	@GetMapping("/{name}")
	public String sayHello(@PathVariable String name) {
		return "Hi "+name+", I'm groot"; 
	}
}
