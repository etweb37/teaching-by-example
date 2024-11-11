import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Map.of;

/**
 * Here is an example of why we inject dependencies
 */
public class WhyDependencyInjection {

    /**
     * OLD SCHOOL
     */
    static class EntityPrinter {
        Set<String> jsonDocuments;

        public EntityPrinter(Set<String> jsonDocuments) {
            this.jsonDocuments = jsonDocuments;
        }

        void print() {

            String prefix = "printing at " + System.currentTimeMillis() + " : ";

            for (String jsonDocument : jsonDocuments) {
                String clean = jsonDocument.replaceAll("asdf", "!!!!");
                // Don't print 'asdf'

                clean = clean.replaceAll("1234", "@@@@");

                // Don't print '6543'
                clean = clean.replaceAll("6543", "####");

                // As you can see we can add lots of these here
                System.out.println(prefix + clean);
            }

        }
    }

    public static void main(String[] args) {

        Set<String> strings = new HashSet<>();
        strings.add("asdf is a great word to say");
        strings.add("1234 is counting 101");
        strings.add("no idea what 6543 is supposed to mean");
        strings.add("lkjh should be asterisks now");

        // Easy enough, we wrote a processor that just prints sanitized texts
        EntityPrinter printer = new EntityPrinter(strings);
        printer.print();

        // but it's not extensible, I have to open the class when I have a new requirement

        // Se here we move the processing rules out and inject them so they can be modified without touching the code in the printer
        // we could now move the replacement values to a property file or service call
        var replacements = of("lkjh", "****", "asdf", "!!!!", "1234", "@@@@", "6543", "####");
        ExtensibleEntityPrinter extensibleEntityPrinter = new ExtensibleEntityPrinter(strings);
        extensibleEntityPrinter.print(replacements);

    }

    static class ExtensibleEntityPrinter {
        Set<String> stringSource;

        public ExtensibleEntityPrinter(Set<String> stringSource) {
            this.stringSource = stringSource;
        }

        void print(Map<String, String> replacements) {
            String prefix = "extensible printing at " + System.currentTimeMillis() + " : ";
            for (String jsonDocument : stringSource) {
                String clean = jsonDocument.trim();
                for (String replacement : replacements.keySet()) {
                    clean = clean.replaceAll(replacement, replacements.get(replacement));
                }
                System.out.println(prefix + clean);
            }

        }
    }

}
