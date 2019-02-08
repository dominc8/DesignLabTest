package de.jonashackt.springbootvuejs.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "iot_nodes")
public class IotNode {

    @Id
    @GenericGenerator(
            name = "iot_node_sequence",
            strategy = "sequence",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "sequence",
                            value = "IOT_NODE_SEQ"
                    )

            })
    @GeneratedValue(generator = "iot_node_sequence")
    private long id;

    @Column
    private String duty1;

    @Column
    private String duty2;

    @Column
    private String duty3;

    @Column
    private String message;

    public IotNode() {
    }

    public IotNode(String duty1, String duty2, String duty3, String message) {
        this.duty1 = duty1;
        this.duty2 = duty2;
        this.duty3 = duty3;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public String getDuty1() {
        return duty1;
    }

    public void setDuty1(String duty1) {
        this.duty1 = duty1;
    }

    public String getDuty2() {
        return duty2;
    }

    public void setDuty2(String duty2) {
        this.duty2 = duty2;
    }

    public String getDuty3() {
        return duty3;
    }

    public void setDuty3(String duty3) {
        this.duty3 = duty3;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
