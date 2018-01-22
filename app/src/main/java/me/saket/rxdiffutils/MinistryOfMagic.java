package me.saket.rxdiffutils;

import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MinistryOfMagic {

  private static final List<Wizard> STUDENTS = new ArrayList<Wizard>() {{
    add(Wizard.create(1, "Hannah Abbott", "Hufflepuff"));
    add(Wizard.create(2, "Sirius Black", "Gryffindor"));
    add(Wizard.create(3, "Lavender Brown", "Gryffindor"));
    add(Wizard.create(4, "Millicent Bulstrode", "Slytherin"));
    add(Wizard.create(5, "Dennis Creevey", "Gryffindor"));
    add(Wizard.create(6, "Roger Davies", "Ravenclaw"));
    add(Wizard.create(7, "Cedric Diggory", "Hufflepuff"));
    add(Wizard.create(8, "Justin Finch-Fletchley", "Hufflepuff"));
    add(Wizard.create(9, "Ernie Macmillan", "Hufflepuff"));
    add(Wizard.create(10, "Zacharias Smith", "Hufflepuff"));
    add(Wizard.create(11, "Vincent Crabbe", "Slytherin"));
    add(Wizard.create(12, "Marcus Flint", "Slytherin"));
    add(Wizard.create(13, "Gregory Goyle", "Slytherin"));
    add(Wizard.create(14, "Draco Malfoy", "Slytherin"));
    add(Wizard.create(15, "Graham Montague", "Slytherin"));
    add(Wizard.create(16, "Pansy Parkinson", "Slytherin"));
    add(Wizard.create(17, "Alicia Spinnet", "Gryffindor"));
    add(Wizard.create(18, "Dean Thomas", "Gryffindor"));
    add(Wizard.create(19, "Ron Weasley", "Gryffindor"));
    add(Wizard.create(20, "Ginny Weasley", "Gryffindor"));
    add(Wizard.create(21, "Katie Bell", "Gryffindor"));
    add(Wizard.create(22, "Colin Creevey", "Gryffindor"));
    add(Wizard.create(23, "Seamus Finnigan", "Gryffindor"));
    add(Wizard.create(24, "Hermione Granger", "Gryffindor"));
    add(Wizard.create(25, "Harry Potter", "Gryffindor"));
  }};

  Single<List<Wizard>> search(String query) {
    if (query.isEmpty()) {
      return Single.just(STUDENTS);
    }

    return Single.fromCallable(() -> STUDENTS.stream()
        .filter(wizard -> wizard.name().toLowerCase(Locale.ENGLISH).startsWith(query.toLowerCase(Locale.ENGLISH)))
        .collect(Collectors.toList()));
  }
}
