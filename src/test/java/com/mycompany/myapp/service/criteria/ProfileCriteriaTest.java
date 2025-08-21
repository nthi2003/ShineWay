package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ProfileCriteriaTest {

    @Test
    void newProfileCriteriaHasAllFiltersNullTest() {
        var profileCriteria = new ProfileCriteria();
        assertThat(profileCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void profileCriteriaFluentMethodsCreatesFiltersTest() {
        var profileCriteria = new ProfileCriteria();

        setAllFilters(profileCriteria);

        assertThat(profileCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void profileCriteriaCopyCreatesNullFilterTest() {
        var profileCriteria = new ProfileCriteria();
        var copy = profileCriteria.copy();

        assertThat(profileCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(profileCriteria)
        );
    }

    @Test
    void profileCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var profileCriteria = new ProfileCriteria();
        setAllFilters(profileCriteria);

        var copy = profileCriteria.copy();

        assertThat(profileCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(profileCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var profileCriteria = new ProfileCriteria();

        assertThat(profileCriteria).hasToString("ProfileCriteria{}");
    }

    private static void setAllFilters(ProfileCriteria profileCriteria) {
        profileCriteria.id();
        profileCriteria.firstName();
        profileCriteria.lastName();
        profileCriteria.phone();
        profileCriteria.email();
        profileCriteria.dob();
        profileCriteria.address();
        profileCriteria.gender();
        profileCriteria.userId();
        profileCriteria.distinct();
    }

    private static Condition<ProfileCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getFirstName()) &&
                condition.apply(criteria.getLastName()) &&
                condition.apply(criteria.getPhone()) &&
                condition.apply(criteria.getEmail()) &&
                condition.apply(criteria.getDob()) &&
                condition.apply(criteria.getAddress()) &&
                condition.apply(criteria.getGender()) &&
                condition.apply(criteria.getUserId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ProfileCriteria> copyFiltersAre(ProfileCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getFirstName(), copy.getFirstName()) &&
                condition.apply(criteria.getLastName(), copy.getLastName()) &&
                condition.apply(criteria.getPhone(), copy.getPhone()) &&
                condition.apply(criteria.getEmail(), copy.getEmail()) &&
                condition.apply(criteria.getDob(), copy.getDob()) &&
                condition.apply(criteria.getAddress(), copy.getAddress()) &&
                condition.apply(criteria.getGender(), copy.getGender()) &&
                condition.apply(criteria.getUserId(), copy.getUserId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
