//
//  AddRecipeView.swift
//  Recipe
//
//  Created by V M on 4.03.24.
//

import SwiftUI

struct AddRecipeView: View {
    
    @EnvironmentObject var recipesVM:RecipesViewModel
    @State private var name: String = ""
    @State private var SelectedCategory: Category = Category.main
    @State private var Description: String = ""
    @State private var Ingredients: String = ""
    @State private var Directions: String = ""
    @State private var NavigateToReciepe = false
    
    @Environment(\.dismiss) var dismiss
    var body: some View {
        NavigationView{
            Form {
                Section(header: Text("Name")) {
                    TextField("Recipe name",text: $name)
                }
                Section(header: Text("Category")){
                    Picker("", selection: $SelectedCategory){
                        ForEach(Category.allCases){category in
                            Text(category.rawValue)
                                .tag(category)
                        }
                    }
                    .pickerStyle(.menu)
                }
                Section(header: Text("Description")){
                    TextEditor(text: $Description)
                }
                Section(header: Text("Ingredients")){
                    TextEditor(text: $Ingredients)
                }
                Section(header: Text("Directions")){
                    TextEditor(text: $Directions)
                }
                
            }
            .toolbar(content: {
                ToolbarItem(placement: .navigationBarLeading){
                    Button{
                        dismiss()
                    } label: {Label("Cancel",systemImage: "xmark")
                            .labelStyle(.iconOnly)
                    }
                   
                    
                }
                ToolbarItem(){
                    NavigationLink(isActive: $NavigateToReciepe){
                        RecipeView(recipe: recipesVM.recipes.sorted{$0.datePublished > $1.datePublished}[0]).navigationBarBackButtonHidden(true)
                    }
                label: {
                    Button{
                        saveRecipe()
                        NavigateToReciepe = true
                    } label: {Label("Done",systemImage: "checkmark")
                            .labelStyle(.iconOnly)
                    }
                    }
              
                .disabled(name.isEmpty)

                }
            })
            .navigationTitle("New Recipe")
            .navigationBarTitleDisplayMode(.inline)
        }
        .navigationViewStyle(.stack)
    }
    
}
struct AddRecipeView_Previews: PreviewProvider {
    static var previews: some View {
        AddRecipeView()
            .environmentObject(RecipesViewModel())
    }
}

extension AddRecipeView{
    private func saveRecipe(){
        let now = Date()
        
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-mm-dd"
        
        let datePublished = dateFormatter.string(from: now)
        print(datePublished)
        let recipe = Recipe(name: name, image: "", description: Description, ingredients: Ingredients, directions: Directions, category: SelectedCategory.rawValue, datePublished: datePublished, url: "")
        
        recipesVM.addRecipe(recipe: recipe)
    }
}
