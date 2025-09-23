package com.mertalptekin.sagaservice.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Configuration
@EnableStateMachine
public class SimpleStateMachineConfig extends StateMachineConfigurerAdapter<String, String> {



    @Override
    public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
        // submitted state ile başla complted state ile tamamlan.
        states
                .withStates()
                .initial("SUBMITTED")
                .state("CHECKING_STOCK")  // karar adımı
                .state("STOCK_RESERVED")
                .state("STOCK_NOT_RESERVED")
                .state("PAYMENT_DONE")
                .state("PAYMENT_FAILED");

    }

    // Eğer state SUBMITTED ise, ve bir event "COMPLETE_ORDER" tetiklenirse,
    //state COMPLETED’e geçsin

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {


        transitions
                // SUBMITTED -> CHECKING_STOCK
                .withExternal().source("SUBMITTED").target("CHECKING_STOCK").event("SUBMIT_ORDER_EVENT").and()
                // CHECKING_STOCK -> STOCK_RESERVED
                .withExternal().source("CHECKING_STOCK").target("STOCK_RESERVED").event("CHECK_STOCK_SUCCESS").and()
                // CHECKING_STOCK -> STOCK_NOT_RESERVED
                .withExternal().source("CHECKING_STOCK").target("STOCK_NOT_RESERVED").event("CHECK_STOCK_FAILED").and()
                // STOCK_RESERVED -> PAYMENT_DONE
                .withExternal().source("STOCK_RESERVED").target("PAYMENT_DONE").event("PAY_SUCCESS").and()
                // STOCK_RESERVED -> PAYMENT_FAILED
                .withExternal().source("STOCK_RESERVED").target("PAYMENT_FAILED").event("PAY_FAILED").and();




    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config) throws Exception {
        config.withConfiguration()
                .listener(new StateMachineListenerAdapter<>() {
                    @Override
                    public void stateChanged(State<String, String> from, State<String, String> to) {
                        System.out.println("State changed from " +
                                (from == null ? "NONE" : from.getId()) +
                                " to " + to.getId());

                        // bridge ile event send etmemiz lazım
                        // consumer içinde ise state machine bağlanıp stateMachine.sendEvent("CHECK_STOCK_SUCCESS");
                        // kodunu çağırmamız lazım, böylece bir stateden diğer state kafka üzerinden gideceğiz.

                    }
                });
    }
}