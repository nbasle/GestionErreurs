public class HelloPetstore {

    // Point d'entrée de l'application
    public static void main(String[] args) {

        // si vous passez l'argument "controlee"
        if (args[0].equals("controlee")) {

            try {
                // Pour une exception controlee, vous êtes obligés
                // de rattraper l'exception et de la traiter
                controlee();
                System.out.println("Ce texte ne s'affichera pas");
            } catch (Exception e) {
                System.out.println("Hello");
            } finally {
                System.out.println("PetStore!");
            }

            // Si vous passez l'argument noncontrolee
        } else {

            // Dans ce cas vous n'etes pas obligés de traiter l'exception
            noncontrolee();
            System.out.println("Ce texte ne s'affichera pas");
        }
    }

    // Une exception controlee doit apparaitre dans la signature
    private static void controlee() throws Exception {
        throw new Exception();
    }

    // Une exception non controlee n'apparait pas dans la signature
    private static void noncontrolee() {
        throw new RuntimeException();
    }
}
