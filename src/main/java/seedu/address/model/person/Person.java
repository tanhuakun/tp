package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.id.HasUniqueId;
import seedu.address.model.id.UniqueId;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.NoOverlapLessonList;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person implements HasUniqueId {

    // Identity fields
    private final Name name;
    private final UniqueId id;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<UniqueId> assignedTaskIds = new HashSet<>();
    private final NoOverlapLessonList lessonsList;
    private final List<Exam> exams = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                  Set<UniqueId> assignedTaskIds, NoOverlapLessonList lessonsList,
                  List<Exam> exams) {
        this.id = UniqueId.generateId(this);
        requireAllNonNull(name, phone, email, address, tags, assignedTaskIds, id, lessonsList, exams);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.assignedTaskIds.addAll(assignedTaskIds);
        this.lessonsList = lessonsList;
        this.exams.addAll(exams);
    }

    /**
     * Every field must be present and not null.
     */
    public Person(UniqueId uniqueId, Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                  Set<UniqueId> assignedTaskIds, NoOverlapLessonList lessonsList, List<Exam> exams) {
        requireAllNonNull(name, phone, email, address, tags, assignedTaskIds, uniqueId, lessonsList, exams);
        this.id = uniqueId;
        uniqueId.setOwner(this);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.assignedTaskIds.addAll(assignedTaskIds);
        this.lessonsList = lessonsList;
        this.exams.addAll(exams);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public UniqueId getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<UniqueId> getAssignedTaskIds() {
        return Collections.unmodifiableSet(assignedTaskIds);
    }

    public NoOverlapLessonList getLessonsList() {
        return lessonsList;
    }

    public List<Exam> getExams() {
        return Collections.unmodifiableList(exams);
    }

    /**
     * Check if person can attend lesson
     *
     * @param lesson lesson to check
     * @return true if person can attend the lesson
     */
    public boolean canAttendLesson(Lesson lesson) {
        return !lessonsList.doesLessonOverlap(lesson);
    }

    /**
     * Immutable way of updating the lessons list. Note that the id will be re-generated.
     *
     * @param newLessonsList to change to
     * @return new Person instance with the updated lessons list
     */
    public Person updateLessonsList(NoOverlapLessonList newLessonsList) {
        return new Person(id, name, phone, email, address, tags, assignedTaskIds, newLessonsList, exams);
    }

    /**
     * Immutable way of updating the assigned task id list
     * @param newAssignedTaskIds the new assigned task id list
     * @return new Person instance with the updated assigned task id list
     */
    public Person updateAssignedTaskIds(Set<UniqueId> newAssignedTaskIds) {
        requireNonNull(newAssignedTaskIds);
        return new Person(id, name, phone, email, address, tags, newAssignedTaskIds, lessonsList, exams);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same id.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        // TODO: make the following code only compare the object id. You will have to face some tedious test fail.
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags())
                && otherPerson.getLessonsList().equals(lessonsList)
                && otherPerson.getExams().equals(exams);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, id, phone, email, address, tags, lessonsList, exams);
    }

    @Override
    public String toString() {
        // TODO: Add individual exams in exams list to string representation!
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Address: ")
                .append(getAddress())
                .append("; Lessons: ")
                .append(getLessonsList())
                .append("; Exams: ")
                .append(getExams());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}
