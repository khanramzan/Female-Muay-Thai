package rams.app.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Our root configuration scans this package for any other {@link Component}'s
 * to register.
 *
 * @author Rob Winch
 *
 */
@Configuration
@ComponentScan
public class RootConfiguration {

}