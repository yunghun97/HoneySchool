import { library } from "@fortawesome/fontawesome-svg-core";
import { fas } from "@fortawesome/free-solid-svg-icons";
import { faInstagram, faTwitter } from "@fortawesome/free-brands-svg-icons";
import { far } from "@fortawesome/free-regular-svg-icons"
import { fab } from "@fortawesome/free-brands-svg-icons"
import FontAwesomeIcon from "../libs/FontAwesomeIcon.vue";
 
library.add(fas, far, fab, faTwitter, faInstagram);
 
export { FontAwesomeIcon };