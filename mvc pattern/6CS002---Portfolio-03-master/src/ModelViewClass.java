public class ModelViewClass {
    public void ModelGUI() {
        SubjectClassO subjectClassO = new SubjectClassO();

        new layoutContO(subjectClassO);
        new playernamelayoutO(subjectClassO);
        new menuClass(subjectClassO);
        new difficultyChoiceClassO(subjectClassO);

        subjectClassO.observernotification();
    }
}
