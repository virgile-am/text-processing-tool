
### Text Processing and Data Management Tool
Overview
The Text Processing and Data Management Tool is a JavaFX-based application that provides powerful features for text processing using regular expressions and managing data collections. The tool allows users to perform search, match, and replace operations on text data and manipulate collections of data using Java collections (List, Set, Map).

# Table of Contents
# Features
# Usage
 


# Features 
**Regex Operations**
Search for patterns in text.
Replace matched patterns with specified text.
Highlight matches within the text.
Supports sets, ranges, alternations, shorthands, and quantifiers.

##USAGE

### Usage  Regex Operations

**Enter Text:** In the Text Input area, input the text you want to process.
**Specify Regex Pattern:** Enter the regex pattern in the Regex Pattern field.
**Replace Text:** (Optional) Enter the replacement text in the Replace With field.
**Find Matches**: Click the Find Matches button to search for the pattern. The matches will be displayed in the Results area.
**Replace Text:** Click the Replace button to replace matches with the specified text. The result will appear in the Results area.
**Highlight Matches:** Click the Highlight Matches button to highlight occurrences of the regex pattern in the text.
### Data Collections
**Select Collection Type:** Choose between List, Set, or Map using the Collection Type dropdown.
**Select Current Collection:** Choose the active collection from the Current Collection dropdown.
**Add Items:** Enter the item in the Item field and click Add to Collection.
**Update Items:** Select an item from the list view, enter the new value in the Item field, and click Update Selected.
**Delete Items:** Select an item from the list view and click Delete Item to remove it from the collection.
**Clear Collection:** Click Clear Collection to remove all items from the active collection.
### Testing
Example Tests
Replace Example

Enter Text: The quick brown fox jumps over the lazy dog.
Regex Pattern: fox
Replace With: cat
Click Replace: Result should be The quick brown cat jumps over the lazy dog.
Highlight Example

Enter Text: The quick brown fox jumps over the lazy dog.
Regex Pattern: \b\w{3}\b
Click Highlight Matches: Result should be [The] quick brown [fox] jumps over [the] lazy dog.
### Data Collection Example
Select List from the Collection Type.
**Add Items:**
Enter apple and click Add to Collection.
Enter banana and click Add to Collection.
**Update Items:**
Select apple, enter pear, and click Update Selected.
apple should be replaced with pear in the list.
**Delete Items:**
Select banana and click Delete Item.
banana should be removed from the list.
**Clear Collection:**
Click Clear Collection.
All items should be removed from the list.
