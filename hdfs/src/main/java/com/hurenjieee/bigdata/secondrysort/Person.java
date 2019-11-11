package com.hurenjieee.bigdata.secondrysort;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Jack
 * @date 2019/7/15 21:42
 */
public  class Person implements WritableComparable<Person> {
    private String name;
    private int age;
    private int salary;

    public Person() {
    }

    public Person(String name, int age, int salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }

    public int compareTo(Person o) {
        int salary = o.salary - this.salary;
        if (salary == 0) {
            return this.age - o.age;
        } else {
            return salary;
        }
    }

    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(name);
        dataOutput.writeInt(age);
        dataOutput.writeInt(salary);
    }

    public void readFields(DataInput dataInput) throws IOException {
        name = dataInput.readUTF();
        age = dataInput.readInt();
        salary = dataInput.readInt();
    }
}
