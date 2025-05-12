import SwiftUI

struct EmptyStateView: View {
    let onRefresh: () -> Void

    var body: some View {
        VStack {
            Text("No todos yet")
                .font(.title2)

            Spacer().frame(height: 12)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}
