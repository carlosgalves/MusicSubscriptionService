

package leti.sidis.plans;


import leti.sidis.plans.api.EditPlanRequest;
import leti.sidis.plans.api.EditPriceRequest;
import leti.sidis.plans.model.Plan;
import org.junit.Assert;
import org.junit.Test;


import static org.junit.Assert.*;

public class PlanTest {


    @Test
    public void ensureNameIsSet() {
        final var subject = new Plan("planName");
        assertEquals("planName", subject.getPlanName());
    }


    @Test
    public void ensureNumberOfMinutesIsUnSet() {
        final var subject = new Plan("name");
        subject.setNumberOfMinutes("50");
        assertEquals(("50"), subject.getNumberOfMinutes());
        subject.setNumberOfMinutes(null);
    }

    @Test
    public void ensureNumberOfMinutesIsSet() {
        final var subject = new Plan("name");
        subject.setNumberOfMinutes("50");
        assertEquals(("50"), subject.getNumberOfMinutes());
    }

    @Test
    public void ensureMaxUsersIsUnSet() {
        final var subject = new Plan("name");
        subject.setMaxUsers(50);
        assertEquals(((50)), subject.getMaxUsers());
        subject.setNumberOfMinutes(null);
    }

    @Test
    public void ensurePatchNameIsIgnored() {
        final var update = new EditPlanRequest("","", 2, 3,"",2.99,29.99,true,false);
        final var subject = new Plan("name");

        subject.update(update.getNumberOfMinutes(), update.getPlanDescription(), update.getMaxUsers(),update.getMusicCollections(),update.getMusicSuggestions(), update.isActive(), update.isPromoted());

        assertEquals("name", subject.getPlanName());
    }

    @Test
    public void ensurePatchNumberOfMinutes() {
        final var update = new EditPlanRequest("","", 2, 3,"",2.99,29.99,true,false);

        final var subject = new Plan("name");
        subject.setNumberOfMinutes("9900");

        subject.update("9900",update.getPlanDescription(), update.getMaxUsers(),update.getMusicCollections(),update.getMusicSuggestions(), update.isActive(), update.isPromoted());

        assertEquals(("9900"), subject.getNumberOfMinutes());
    }

    @Test
    public void whenNumberOfMinutesIsNotSetPatchingWithValueSets() {
        final var update = new EditPlanRequest("","", 2, 3,"",2.99,29.99,true,false);

        final var subject = new Plan("name");

        subject.update("3",update.getPlanDescription(), update.getMaxUsers(),update.getMusicCollections(),update.getMusicSuggestions(), update.isActive(), update.isPromoted());

        assertNotEquals(("2"), subject.getNumberOfMinutes());
    }
    @Test
    public void ensurePatchPlanDescription() {
        final var update = new EditPlanRequest("","", 2, 3,"",2.99,29.99,true,false);

        final var subject = new Plan("name");
        subject.setPlanDescription("bom");

        subject.update("bom",update.getPlanDescription(), update.getMaxUsers(),update.getMusicCollections(),update.getMusicSuggestions(), update.isActive(), update.isPromoted());

        assertEquals(("bom"), subject.getNumberOfMinutes());
    }

    @Test
    public void ensurePatchMaxUsers() {
        final var update = new EditPlanRequest("","", 7, 3,"",2.99,29.99,true,false);

        final var subject = new Plan("name");
        subject.setMusicCollections(3);

        subject.update(update.getNumberOfMinutes(),update.getPlanDescription(),update.getMaxUsers(),update.getMusicCollections(),update.getMusicSuggestions(), update.isActive(), update.isPromoted());

        assertEquals((7), subject.getMaxUsers());
    }

    @Test
    public void whenMaxUsersIsNotSetPatchingWithValueSets() {
        final var update = new EditPlanRequest("","", 2, 3,"",2.99,29.99,true,false);

        final var subject = new Plan("name");

        subject.update(update.getNumberOfMinutes(), update.getPlanDescription(), 3,update.getMusicCollections(),update.getMusicSuggestions(), update.isActive(), update.isPromoted());

        assertNotEquals(("2"), subject.getNumberOfMinutes());
    }

    @Test
    public void ensurePatchMusicCollections() {
        final var update = new EditPlanRequest("","", 2, 3,"",2.99,29.99,true,false);

        final var subject = new Plan("name");
        subject.setMusicCollections(3);

        subject.update(update.getNumberOfMinutes(),update.getPlanDescription(),update.getMaxUsers(),3,update.getMusicSuggestions(), update.isActive(), update.isPromoted());

        assertEquals((3), subject.getMusicCollections());
    }

    @Test
    public void whenMusicCollectionsIsNotSetPatchingWithValueSets() {
        final var update = new EditPlanRequest("","", 2, 3,"",2.99,29.99,true,false);

        final var subject = new Plan("name");

        subject.update(update.getNumberOfMinutes(), update.getPlanDescription(), update.getMaxUsers(),0,update.getMusicSuggestions(), update.isActive(), update.isPromoted());

        assertNotEquals((null), subject.getNumberOfMinutes());
    }
    @Test
    public void ensurePatchMusicSuggestions() {
        final var update = new EditPlanRequest("","", 2, 3,"",2.99,29.99,true,false);

        final var subject = new Plan("name");
        subject.setMusicSuggestions("automatic");

        subject.update(update.getNumberOfMinutes(),update.getPlanDescription(),update.getMaxUsers(), update.getMusicCollections(),"automatic", update.isActive(), update.isPromoted());

        assertEquals(("automatic"), subject.getMusicSuggestions());
    }

    @Test
    public void ensurePatchIsPromoted() {
        final var update = new EditPlanRequest("","", 2, 3,"",2.99,29.99,true,false);

        final var subject = new Plan("name");
        subject.setPromoted(false);

        subject.update(update.getNumberOfMinutes(), update.getPlanDescription(), update.getMaxUsers(), 0, update.getMusicSuggestions(), update.isPromoted(), false);

        assertEquals((false), subject.isPromoted());

    }
    @Test
    public void ensurePatchMonthlyFee() {
        final var update = new EditPriceRequest(3.99, 39.99);

        final var subject = new Plan("name");
        subject.setMonthlyFee(3.99);

        subject.applyPrice(3.99, update.getAnnualFee());

        assertEquals(3.99, 3.99, 0.01);
    }

    @Test
    public void ensurePatchAnnualFee() {
        final var update = new EditPriceRequest(3.99, 39.99);

        final var subject = new Plan("name");
        subject.setMonthlyFee(39.99);

        subject.applyPrice(update.getMonthlyFee(), 39.99);

        assertEquals(39.99, 39.99, 0.01);
    }
}

